#include <jni.h>
#include <string>
#include <stdio.h>
#include <cstring>
#include <stdlib.h>
#include <android/log.h>
extern "C"
JNIEXPORT jstring

JNICALL
Java_com_example_administrator_debugnative_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_example_administrator_debugnative_LeakNotify_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "123";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_administrator_debugnative_MainActivity_testMalloc(JNIEnv *env, jobject thiz) {
    unsigned int size=10*1024*1024;
    void* p = malloc(size);
    memset(p,255,size);
//    new char[size]();
    __android_log_print(ANDROID_LOG_ERROR,"malloc","malloc 10M");
}