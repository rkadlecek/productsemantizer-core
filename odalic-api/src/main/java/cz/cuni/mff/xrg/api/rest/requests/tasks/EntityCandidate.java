package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class EntityCandidate {

    private Entity entity;
    private Score score;

    public EntityCandidate() { super(); }

    public Entity getEntity() {
        return entity;
    }

    public Score getScore() {
        return score;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "EntityCandidate [entity=" + this.entity + ", score=" + this.score + "]";
    }
}
