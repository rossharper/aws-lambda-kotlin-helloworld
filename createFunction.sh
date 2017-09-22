#!/bin/bash

awsaccountid=$1

if [[ -n "$awsaccountid" ]]; then
	aws lambda create-function --region eu-west-1 --function-name kotlin-hello \
	--zip-file fileb://build/libs/aws-lambda-kotlin-helloworld-1.0-SNAPSHOT-all.jar \
	--role arn:aws:iam::$awsaccountid:role/HelloKotlinLambdaRole \
	--handler net.rossharper.awshelloworld.Main::handler --runtime java8 \
	--timeout 15 --memory-size 128
else
	echo "Usage: createFunction.sh <AWS_ACCOUNT_ID>"
fi
