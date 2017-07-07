#!/usr/bin/env bash

# Script to generate the doc/API.html API documentation file from the API.apib API blueprint file.
# Uses https://github.com/danielgtaylor/aglio tool to generate the HTML.

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

aglio -i "${DIR}/API.apib" -o "${DIR}/doc/API.html"

