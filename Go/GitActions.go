// GitActions.go
package main

import "fmt"

// Multiplica é uma função pública para multiplicar dois números
func Multiplica(a, b int) int {
    return a * b
}

func main() {
    resultado := Multiplica(3, 4)
    fmt.Println("Resultado da multiplicação:", resultado)
}