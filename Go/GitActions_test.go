package main

import (
	"testing"
	"./GitActions.go"
)
func Testcalcula(t *testing.T) {

	total := calcula(2, 2)

	if total != 4 {
		t.Errorf("Valor incorreto / Resultado: %d Esperado: %d", total, 4)
	}
}
