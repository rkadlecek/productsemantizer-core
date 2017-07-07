package sk.kadlecek.productsemantizer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cuni.mff.xrg.api.rest.requests.files.FormatRequest;
import cz.cuni.mff.xrg.api.rest.requests.tasks.*;
import cz.cuni.mff.xrg.api.rest.requests.users.Credentials;
import cz.cuni.mff.xrg.api.rest.responses.Reply;
import cz.cuni.mff.xrg.api.rest.responses.bases.KnowledgeBaseResponse;
import cz.cuni.mff.xrg.api.rest.responses.users.AuthResponse;
import org.apache.commons.lang3.Conversion;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sk.kadlecek.productsemantizer.bean.*;
import sk.kadlecek.productsemantizer.bean.KnowledgeBase;
import sk.kadlecek.productsemantizer.enumeration.JobState;
import sk.kadlecek.productsemantizer.exception.FileStorageException;
import sk.kadlecek.productsemantizer.exception.OdalicApiException;
import sk.kadlecek.productsemantizer.util.ConversionUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service("OdalicApiService")
public class HttpOdalicApiServiceImpl implements OdalicApiService {

    private final static Logger logger = LoggerFactory.getLogger(HttpOdalicApiServiceImpl.class);
    private final ConfigurationService configurationService;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    private final List<Integer> EXPECTED_CODES_UPLOAD_FILE = new ArrayList<Integer>() {{
        add(HttpStatus.SC_CREATED);
        add(HttpStatus.SC_OK);
    }};

    private final List<Integer> EXPECTED_CODES_CONFIGURE_FILE = new ArrayList<Integer>() {{
        add(HttpStatus.SC_OK);
    }};

    private final List<Integer> EXPECTED_CODES_CREATE_TASK = new ArrayList<Integer>() {{
        add(HttpStatus.SC_CREATED);
        add(HttpStatus.SC_OK);
    }};

    private final List<Integer> EXPECTED_CODES_RUN_TASK = new ArrayList<Integer>() {{
        add(HttpStatus.SC_OK);
    }};

    private final List<Integer> EXPECTED_CODES_GET_TASK_STATE = new ArrayList<Integer>() {{
        add(HttpStatus.SC_OK);
    }};

    private final String URI_AUTH = "/users/authentications";
    private final String URI_CREATE_TASK = "/tasks/%s"; //PUT
    private final String URI_RUN_TASK = "/tasks/%s/execution"; //PUT
    private final String URI_GET_TASK_STATE = "/tasks/%s/state"; // GET

    private final String URI_GET_KNOWLEDGE_BASES = "/bases"; // GET

    private final String URI_UPLOAD_FILE = "/files/%s"; // PUT
    private final String URI_CONFIGURE_FILE = "/files/%s/format"; // PUT

    private String authToken = null;

    @Autowired
    public HttpOdalicApiServiceImpl(ConfigurationService configurationService,
                                    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
                                    FileStorageService fileStorageService) {
        super();
        Assert.notNull(configurationService, "ConfigurationService must not be null!");
        Assert.notNull(mappingJackson2HttpMessageConverter, "MappingJackson2HttpMessageConverter must not be null!");
        Assert.notNull(fileStorageService, "FileStorageService must not be null!");
        this.configurationService = configurationService;
        this.objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
        this.fileStorageService = fileStorageService;
    }

    @Override
    public String uploadJobFile(Job job) throws OdalicApiException, FileStorageException {

        File file = fileStorageService.getRepositoryFile(job.getInputFilePath());
        String fileIdentifier = file.getName();

        String url = String.format(configurationService.getOdalicBaseUri() + URI_UPLOAD_FILE, fileIdentifier);

        HttpPut request = new HttpPut(url);
        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("input", file, ContentType.create("text/csv"), file.getName())
                .build();

        request.setEntity(entity);

        performHttpUriRequest(request, getAuthToken(), EXPECTED_CODES_UPLOAD_FILE);

        return fileIdentifier;
    }

    private void configureUploadedFile(Job job, String odalicFileIdentifier) throws OdalicApiException {
        String url = String.format(configurationService.getOdalicBaseUri() + URI_CONFIGURE_FILE, odalicFileIdentifier);

        FormatRequest entity = new FormatRequest();
        entity.setDelimiter(job.getInputFileColumnSeparator());
        entity.setQuoteCharacter(job.getInputFileColumnEnclosing());

        performPutRequest(url, entity, getAuthToken(), EXPECTED_CODES_CONFIGURE_FILE);
    }

    @Override
    public String createTask(Job job) throws OdalicApiException, FileStorageException {
        Assert.notEmpty(job.getOdalicConfiguration().getKnowledgeBases(), "KnowledgeBases can not be empty!");

        String odalicFileIdentifier = uploadJobFile(job);
        configureUploadedFile(job, odalicFileIdentifier);

        String taskId = ConversionUtils.jobTitleToOdalicId(job.getTitle());
        TaskValue taskValue = new TaskValue();
        taskValue.setId(taskId);
        taskValue.setCreated(new Date());
        taskValue.setDescription(job.getDescription());

        ConfigurationValue cv = new ConfigurationValue();
        cv.setInput(odalicFileIdentifier);
        cv.setFeedback(new Feedback());

        cv.setPrimaryBase(
            ConversionUtils.knowledgeBaseToOdalicKnowledgeBase(job.getOdalicConfiguration().getKnowledgeBases().get(0))
        );
        cv.setUsedBases(

            new TreeSet<>(
                job.getOdalicConfiguration().getKnowledgeBases().stream()
                    .map(ConversionUtils::knowledgeBaseToOdalicKnowledgeBase)
                    .collect(Collectors.toSet())
            )
        );

        cv.setRowsLimit(job.getOdalicConfiguration().getRowsLimit());
        cv.setStatistical(job.getOdalicConfiguration().getStatistical());

        taskValue.setConfiguration(cv);

        //{"id":"Jugendzentren2",
        // "created":"2017-05-30 21:47",
        // "configuration":{
        //      "input":"Jugendzentren.csv",
        //      "feedback":{
        //          "columnIgnores":[],
        //          "classifications":[],
        //          "columnAmbiguities":[],
        //          "ambiguities":[],
        //          "disambiguations":[],
        //          "columnRelations":[]},
        //      "primaryBase":{"name":"DBpedia"},
        //      "usedBases":[{"name":"DBpedia"},{"name":"German DBpedia"}],
        //      "rowsLimit":null,"
        //      statistical":false},
        // "description":"test test test"}

        String url = String.format(configurationService.getOdalicBaseUri() + URI_CREATE_TASK, taskId);
        performPutRequest(url, taskValue, getAuthToken(), EXPECTED_CODES_CREATE_TASK);
        return taskId;
    }

    @Override
    public void runTask(Job job) throws OdalicApiException {

        ExecutionValue executionValue = new ExecutionValue() {{ setDraft(false); }};

        String taskId = job.getJobStatus().getOdalicTaskId();
        String url = String.format(configurationService.getOdalicBaseUri() + URI_RUN_TASK, taskId);
        performPutRequest(url, executionValue, getAuthToken(), EXPECTED_CODES_RUN_TASK);
    }

    @Override
    public JobState getTaskStatus(Job job) throws OdalicApiException {

        String taskId = ConversionUtils.jobTitleToOdalicId(job.getTitle());
        String url = String.format(configurationService.getOdalicBaseUri() + URI_GET_TASK_STATE, taskId);
        Reply reply = performGetRequest(url, getAuthToken(), EXPECTED_CODES_GET_TASK_STATE);

        // parse response and return state
        StateValue state = objectMapper.convertValue(reply.getPayload(), StateValue.class);
        // TODO convert to some productsemantizer status
        return ConversionUtils.odalicStatusValueToJobState(state);
    }

    @Override
    public Set<KnowledgeBase> getSupportedKnowledgeBases() throws OdalicApiException {
        String url = configurationService.getOdalicBaseUri() + URI_GET_KNOWLEDGE_BASES;
        Reply reply = performGetRequest(url, getAuthToken(), EXPECTED_CODES_GET_TASK_STATE);

        List<KnowledgeBaseResponse> bases =
                objectMapper.convertValue(reply.getPayload(), new TypeReference<List<KnowledgeBaseResponse>>(){});

        return bases.stream()
                .map(kb -> ConversionUtils.knowledgeBaseResponseToKnowledgeBase(kb))
                .collect(Collectors.toSet());
    }

    private String getAuthToken() throws OdalicApiException {
        if (authToken == null) {
            AuthResponse authResponse = logIn();
            authToken = authResponse.getToken();
            logger.debug("Logged In");
        }
        return authToken;
    }

    private AuthResponse logIn() throws OdalicApiException {
        String username = configurationService.getOdalicUsername();
        String password = configurationService.getOdalicPassword();

        String url = configurationService.getOdalicBaseUri() + URI_AUTH;

        try {
            Reply reply = performPostRequest(url, new Credentials(username, password), null,
                    new ArrayList<Integer>() {{ add(HttpStatus.SC_OK); }});
            return objectMapper.convertValue(reply.getPayload(), AuthResponse.class);
        } catch (Exception e) {
            throw new OdalicApiException("Failed to Log In,", e);
        }
    }

    private HttpClient buildHttpClient() {
        return HttpClientBuilder.create().build();
    }

    private Reply performGetRequest(String url, String authToken, List<Integer> expectedHttpStatusCodes) throws OdalicApiException {
        HttpGet request = new HttpGet(url);
        return performHttpUriRequest(request, authToken, expectedHttpStatusCodes);
    }

    private Reply performPostRequest(String url, Object entity, String authToken,
                                     List<Integer> expectedHttpStatusCodes) throws OdalicApiException {
        HttpPost request = new HttpPost(url);
        return performEntityRequest(request, entity, authToken, expectedHttpStatusCodes);
    }

    private Reply performPutRequest(String url, Object entity, String authToken,
                                    List<Integer> expectedHttpStatusCodes) throws OdalicApiException {
        HttpPut request = new HttpPut(url);
        return performEntityRequest(request, entity, authToken, expectedHttpStatusCodes);
    }

    private Reply performEntityRequest(HttpEntityEnclosingRequestBase requestBase, Object entity,
                                       String authToken, List<Integer> expectedHttpStatusCodes) throws OdalicApiException {

        try {
            if (entity != null) {
                StringEntity requestEntity = new StringEntity(objectMapper.writeValueAsString(entity), ContentType.APPLICATION_JSON);
                requestBase.setEntity(requestEntity);
            }
            return performHttpUriRequest(requestBase, authToken, expectedHttpStatusCodes);
        }catch (JsonProcessingException e) {
            throw new OdalicApiException("Failed to perform POST request: {}", e);
        }
    }


    private Reply performHttpUriRequest(HttpUriRequest request, String authToken,
                                        List<Integer> expectedHttpStatusCodes) throws OdalicApiException {
        HttpClient client = buildHttpClient();
        if (authToken != null) {
            request.addHeader("Authorization", "Bearer " + authToken);
        }

        try {
            HttpResponse response = client.execute(request);
            if (expectedHttpStatusCodes.contains(response.getStatusLine().getStatusCode())) {
                Reply reply = objectMapper.readValue(response.getEntity().getContent(), Reply.class);
                logger.debug("Got reply: {}", reply);
                return reply;
            } else {
                logger.debug("Got invalid response: {}", response.getEntity().getContent());
                throw new OdalicApiException("Unexpected status code: " + response.getStatusLine().getStatusCode() +
                    " (expected one of: [" + String.join(",",
                        expectedHttpStatusCodes
                                .stream()
                                .map(x -> x.toString())
                                .collect(Collectors.toList())) + "]).");
            }
        } catch (IOException e) {
            throw new OdalicApiException("Failed to Log In,", e);
        }
    }

}
