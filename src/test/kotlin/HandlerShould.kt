package net.rossharper.awshelloworld

import org.junit.Test
import java.io.ByteArrayOutputStream

class HandlerShould {

    @Test
    fun not_a_test_but_a_spike_using_moshi_with_okio() {
        val input = "{\"resource\":\"/test\",\"path\":\"/test\",\"httpMethod\":\"POST\",\"headers\":null,\"queryStringParameters\":null,\"pathParameters\":null,\"stageVariables\":null,\"requestContext\":{\"path\":\"/test\",\"accountId\":\"373806372146\",\"resourceId\":\"4hcbe8\",\"stage\":\"test-invoke-stage\",\"requestId\":\"test-invoke-request\",\"identity\":{\"cognitoIdentityPoolId\":null,\"accountId\":\"373806372146\",\"cognitoIdentityId\":null,\"caller\":\"AIDAIVLN52O7EH3NKQTVG\",\"apiKey\":\"test-invoke-api-key\",\"sourceIp\":\"test-invoke-source-ip\",\"accessKey\":\"ASIAJVOYDV6SFJL7F5KQ\",\"cognitoAuthenticationType\":null,\"cognitoAuthenticationProvider\":null,\"userArn\":\"arn:aws:iam::373806372146:user/Administrator\",\"userAgent\":\"Apache-HttpClient/4.5.x (Java/1.8.0_131)\",\"user\":\"AIDAIVLN52O7EH3NKQTVG\"},\"resourcePath\":\"/test\",\"httpMethod\":\"POST\",\"apiId\":\"gxkb6e8i28\"},\"body\":\"{\\\"name\\\":\\\"Ross\\\"}\",\"isBase64Encoded\":false}"
        val inputStream = input.byteInputStream()

        val outputStream = ByteArrayOutputStream()

        Main().handler(inputStream, outputStream)

        val outputStr = outputStream.toString()

        println(outputStr)
    }
}