# SimplexAlternativo

Criar um projeto no eclipse com a biblioteca json-simple-1.1.1.1.jar

A entrada é um arquivo json sendo:

"qtdRestricoes": inteiro com quantidade de restrições
"qtdVariaveis": inteiro com quantidade de variaveis
"maximizarZ": true = maximização; false minimização
"z": Array de coeficientes função objetivo --> tamanho deve ser igual ao número de variáveis
"ld": Array de coeficientes do lado direito --> tamanho deve ser igual ao número de restrições --> devese colocar as funções em ordem menor, maior e igual
"var": { Objeto com as restrições de variáveis
    "menorIgualZero": []--índice das variaveis menores ou iguais a 0
    "maiorIgualZero": []--índice das variaveis maiores ou iguais a 0
    "irrestrita": []--índice das variaveis pertencentes ao conjunto dos reais
}-- um índice não deve estar em mais de um campo e os indices devem ir de 1 à té o número de variaveis
"restricoes": { --  Objeto com as restrições
    "menorIgual": { -- restrições menor ou igual ao respectivo lado direito
      "me1":[], -- deve ter o tamanho igual a quantidade de variaveis
      "me2":[] -- deve seguir a ordem me1, me2, me3 ....
    },
    "maiorIgual": { -- restrições maior ou igual ao respectivo lado direito
      "ma1":[], -- deve seguir a ordem ma1, ma2, ma3 ....
      "ma2":[]
     },
    "igual": { -- restrições exatamente igual ao respectivo lado direito
      "i1":[],-- deve seguir a ordem i1, i2, i3 ....
      "i2":[]
     } -- a quantidade de restrições deve ser igual ao valor informado em qtdRestricoes
}

##Exemplo

MIN ( z = 2x1+3x2 +9x3 )

r1 --> 2x1+3x3>=7
r2 --> 3x1+4x2<=200
r3 --> -5x1-2x2 -600x3 <=-500
r4 --> 90x1-90x3 = 30

x1>=0; {x2 e R}; x3 <=0

{
    "qtdRestricoes": 4,
    "qtdVariaveis": 3,
    "maximizarZ": false,
    "z": [2,2,9],
    "ld": [200,-500,7,30],
    "var": {
        "menorIgualZero": [3],
        "maiorIgualZero": [1],
        "irrestrita": [2]
    },
    "restricoes": {
        "menorIgual": {
          "me1":[3,4,0],
          "me2":[-5,-2,-600]
        },
        "maiorIgual": {
          "ma1":[2,0,3]
         },
        "igual": {
          "i1":[90,0,-90]
         }
    }
}




