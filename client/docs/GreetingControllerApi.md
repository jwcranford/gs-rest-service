# GreetingControllerApi

All URIs are relative to *https://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**greetingUsingGET**](GreetingControllerApi.md#greetingUsingGET) | **GET** /greeting | greeting


<a name="greetingUsingGET"></a>
# **greetingUsingGET**
> Greeting greetingUsingGET(name)

greeting

### Example
```java
// Import classes:
//import hello.client.ApiException;
//import hello.client.api.GreetingControllerApi;


GreetingControllerApi apiInstance = new GreetingControllerApi();
String name = "World"; // String | name
try {
    Greeting result = apiInstance.greetingUsingGET(name);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GreetingControllerApi#greetingUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**| name | [optional] [default to World]

### Return type

[**Greeting**](Greeting.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

