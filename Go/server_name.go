package main

import (
	"fmt"
	"net/http"
	"os"
)

func main() {
	http.HandleFunc("/", Ola)
	http.ListenAndServe(":5000", nil)
}

func Ola(w http.ResponseWriter, r *http.Request) {
	hostName, err := os.Hostname()
	if err != nil {
		fmt.Println("Erro ao obter o nome do host:", err)
		hostName = "Nome do host não disponível"
	}

	message := fmt.Sprintf("<h1>Olá Ludvig! Este servidor está rodando em: %s</h1>", hostName)
	w.Write([]byte(message))
}
