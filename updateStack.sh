aws cloudformation update-stack \
--template-file sam_template.yaml \
--stack-name awshelloworld \
--region eu-west-1 \
--capabilities CAPABILITY_IAM
