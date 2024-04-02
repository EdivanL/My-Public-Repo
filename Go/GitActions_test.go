// GitActions_test.go
package main

import (
    "testing"
)

func TestMultiplica(t *testing.T) {
    resultado := Multiplica(3, 4)
    esperado := 12
    if resultado != esperado {
        t.Errorf("Resultado incorreto. Esperado: %d, Obtido: %d", esperado, resultado)
    }
}
