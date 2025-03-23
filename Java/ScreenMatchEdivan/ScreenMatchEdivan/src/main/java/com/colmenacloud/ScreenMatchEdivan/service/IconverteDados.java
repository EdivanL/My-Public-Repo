package com.colmenacloud.ScreenMatchEdivan.service;

public interface IconverteDados {
    <T>  T obterDados(String json, Class<T> classe);
}
