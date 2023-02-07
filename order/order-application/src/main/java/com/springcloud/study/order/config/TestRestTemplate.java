package org.training.cloud.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * RestTemplate 测试
 *
 * @author wangtongzhou
 * @since 2020-12-11 12:22
 */
public class TestRestTemplate extends RestTemplate {

    @Autowired
    private DiscoveryClient discoveryClient;



    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        Assert.notNull(url, "url必填");
        Assert.notNull(method, "方法必填");

        ClientHttpResponse response = null;

        url = replaceUrl(url);
        try {
            ClientHttpRequest request = createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }
            response = request.execute();
            handleResponse(url, method, response);
            return responseExtractor != null ?
                    responseExtractor.extractData(response) : null;
        } catch (IOException e) {
            String resource = url.toString();
            String query = url.getRawQuery();
            resource = query != null ? resource.substring(0, resource.indexOf('?')) :
                    resource;
            throw new ResourceAccessException("I/O error on" + method.name() +
                    "request for" + resource + ":" + e.getMessage(), e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 替换url
     *
     * @param url url
     * @return url
     */
    private URI replaceUrl(URI url) {
        String sourceUrl = url.toString();
        String[] httpUrl = sourceUrl.split("//");
        int index = httpUrl[1].replaceFirst("/", "@").indexOf("@");
        String serviceName = httpUrl[1].substring(0, index);
        //获取实例
        List<ServiceInstance> serviceInstanceList =
                discoveryClient.getInstances(serviceName);

        if (CollectionUtils.isEmpty(serviceInstanceList)) {
            throw new RuntimeException("没有可用的微服务实例列表" + serviceName);
        }

        Random random = new Random();
        Integer randomIndex = random.nextInt(serviceInstanceList.size());
        String serviceIp =
                serviceInstanceList.get(randomIndex).getUri().toString();
        String targetSource = httpUrl[1].replace(serviceName, serviceIp);

        try {
            return new URI(targetSource);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        return url;
    }
}
