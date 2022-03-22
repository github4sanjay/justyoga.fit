package com.justyoga.util.config;

import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import feign.Response;
import feign.codec.ErrorDecoder;

public class MyErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            return new AppException("Bad request", AppStatusCode.INTERNAL_ERROR);
        }
        /*String message = null;
        Reader reader = null;

        try {
            reader = response.body().asReader();
            //Easy way to read the stream and get a String object
            String result = CharStreams.toString(reader);
            //use a Jackson ObjectMapper to convert the Json String into a
            //Pojo
            ObjectMapper mapper = new ObjectMapper();
            //just in case you missed an attribute in the Pojo
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //init the Pojo
            BaseResponse baseResponse = mapper.readValue(result,
                    BaseResponse.class);

            message = baseResponse.getData();


        } catch (IOException e) {

            e.printStackTrace();
        }finally {

            //It is the responsibility of the caller to close the stream.
            try {

                if (reader != null)
                    reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (response.status()) {

            case 404:
                return new FileNotFoundException(message == null ? "File no found" :
                        message);
            case 403:
                return new ForbiddenAccessException(message == null ? "Forbidden
                        access" : message);

        }*/

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
