NAME=hellokotlinlambdaworld
APINAME="${NAME}api"
REGION="eu-west-1"

aws apigateway create-rest-api \
--name "${APINAME}" \
--description "Api for ${NAME}" \
--region "${REGION}"

APIID=$(aws apigateway get-rest-apis --query "items[?name==\`${APINAME}\`].id" --output text --region ${REGION})

PARENTRESOURCEID=$(aws apigateway get-resources --rest-api-id ${APIID} --query 'items[?path==`/`].id' --output text --region ${REGION})

aws apigateway create-resource \
--rest-api-id ${APIID} \
--parent-id ${PARENTRESOURCEID} \
--path-part test \
--region ${REGION}

RESOURCEID=$(aws apigateway get-resources --rest-api-id ${APIID} --query 'items[?path==`/test`].id' --output text --region ${REGION})

aws apigateway put-method \
--rest-api-id ${APIID} \
--resource-id ${RESOURCEID} \
--http-method POST \
--authorization-type NONE \
--region ${REGION}

aws apigateway put-integration --rest-api-id ${APIID} \
--resource-id ${RESOURCEID} \
--http-method POST \
--type AWS \
--integration-http-method POST \
--uri arn:aws:apigateway:${REGION}:lambda:path/2015-03-31/functions/${LAMBDAARN}/invocations \
--request-templates '{"application/x-www-form-urlencoded":"{\"body\": $input.json(\"$\")}"}'
--region ${REGION}