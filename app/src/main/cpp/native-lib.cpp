#include <jni.h>
#include <string>
#include <stdio.h>
#include <cstring>
#include <stdlib.h>
#include <android/log.h>


class A {
public:
    int num = 1;

    ~A() {
        __android_log_print(ANDROID_LOG_ERROR, "test", "~A: %d\n", this->num);
    }

    void log() {
        __android_log_print(ANDROID_LOG_ERROR, "test", "~A log: %d\n", this->num);
    }
};

class B : public A {
public:
    B(int num) {
        this->num = num;
    }

    ~B() {
        __android_log_print(ANDROID_LOG_ERROR, "test", "~B: %d\n", this->num);
    }
};

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_example_administrator_debugnative_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

    B *p = new B(20);
    __android_log_print(ANDROID_LOG_ERROR, "test", "before delete: %d\n", p->num);
    delete p;

    __android_log_print(ANDROID_LOG_ERROR, "test", "after delete: %d\n", p->num);
    p->log();



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