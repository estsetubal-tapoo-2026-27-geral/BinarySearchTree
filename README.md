# A2.4 | Árvores binárias de pesquisa e ADT Map

## Objetivos

Distinguir o ADT `Map<K,V>` da estrutura ABP; analisar inserção e os três casos de remoção; implementar pesquisa recursiva e travessia em ordem; verificar a relação entre a altura e o custo das operações.

## Atividade

1. Desenhar a árvore obtida por `put` com as chaves `8, 3, 12, 1, 6, 10, 14`. Indicar o caminho da pesquisa de 10 e 7. Localizar o mínimo da subárvore direita de 8.
2. Ler o código de `put`, `insertBST` e `remove`. Explicar o resultado de inserir novamente a chave 6 com outro valor. Anotar em que linhas se resolvem os casos de remoção de folha, nó com um filho e nó com dois filhos.
3. Implementar `searchNodeWithKey(K key, BSTNode treeRoot)`: devolver o nó encontrado ou `null`, usando recursão e `compareTo`.
4. Implementar `getLeftmostNode(BSTNode treeRoot)`: devolver `null` para subárvore vazia; caso contrário, seguir `left` até ao mínimo.
5. Implementar `appendKeys` e `appendValues` por travessia **esquerda → nó → direita** (travessia Pré-order). As coleções devolvidas são cópias; os valores aparecem pela ordem das respetivas chaves.
6. Executar `Main` com os pares `(8, Ana)`, `(3, Pedro)`, `(12, Jose)`, `(1, Jose)`, `(6, Beatriz)`, `(10, Rui)` e `(14, Luis)`. 
   - Explicar por que razão duas chaves podem ter o mesmo valor.
   - Explicar porque é que a execução do código  `System.out.println("Chaves: " + map.keys());`lista as chaves por ordem crescente. (Revisite o código da implementação de `keys()`)
8. Criar testes unitários JUnit para `MapBST`, seguindo a lista abaixo. Cada teste deve construir o seu próprio mapa; não reutilizar uma instância alterada por outros testes.
8. Explicar por que razão uma árvore construída com chaves inseridas por ordem crescente pode perder eficiência.

## Orientações de implementação

### Pesquisa: `searchNodeWithKey` e `get`

Em `searchNodeWithKey(K key, BSTNode treeRoot)`, tratar primeiro o caso base `treeRoot == null` (devolver `null`). Comparar `key` com `treeRoot.key` através de `compareTo`: igualdade devolve o nó; uma chave menor leva à chamada recursiva na subárvore esquerda; uma maior leva à direita. Traçar as chamadas para uma chave existente (10) e uma ausente (7) antes de escrever o código.

`get(key)` rejeita uma chave `null`, usa esta pesquisa e devolve o **valor do nó** encontrado ou `null` se não existir. `containsKey` usa a mesma pesquisa, mas devolve um booleano. Se o mapa admitir valores `null`, `get(key) == null` não prova que a chave está ausente; confirmar com `containsKey`.

### Inserção e substituição: `put`

Ler a implementação disponibilizada de `put` e `insertBST`. 
- Para uma árvore vazia, criar a raiz. Caso contrário, comparar a chave com a do nó atual e seguir à esquerda ou à direita até uma ligação vazia, onde se cria um novo nó. 
- Se a **chave já existir**, atualizar apenas o valor no mesmo nó, devolver o valor antigo e não alterar o número de mapeamentos. 
  - Por exemplo, depois de `put(6, "Beatriz")`, a chamada `put(6, "novo")` devolve `"Beatriz"`, mantém o tamanho e faz `get(6)` devolver `"novo"`. A comparação é sempre entre chaves; valores iguais podem pertencer a chaves diferentes.

### Remoção: `remove` e mínimo da subárvore

Ler `remove` e localizar no código os três casos: 
(1) nó sem filhos, desligá-lo do pai; 
(2) nó com um filho, ligar o filho ao pai do nó removido; 
(3) nó com dois filhos, obter o **mínimo da subárvore direita**, retirar o sucessor da posição antiga e copiar a respetiva chave e valor para o nó original. Em todos os casos, devolver o valor que estava associado à chave pedida, ou `null` se ela não existir. Confirmar separadamente os casos que alteram `root` e atualizar a referência `parent` do filho que passa a ocupar o lugar do nó removido.

Implementar `getLeftmostNode(BSTNode treeRoot)`: devolver `null` para subárvore vazia; caso contrário, seguir `left` até ao nó mais à esquerda. O código de remoção fornecido utiliza este auxiliar para localizar o sucessor no terceiro caso.

### Percursos: `keys` e `values`

Completar `appendKeys` e `appendValues` com a sequência **subárvore esquerda → nó → subárvore direita** (percurso em in-order). 
- `keys()` recolhe chaves ordenadas; 
- `values()` recolhe os valores associados **pela ordem das chaves**, mesmo quando os valores não estão ordenados. 
  - Por exemplo, para `(3, "Z"), (1, "A"), (6, "B")`, obter `keys() = [1, 3, 6]` e `values() = [A, Z, B]`. Em ambos os auxiliares, terminar imediatamente quando a subárvore for `null`.

## Testes unitários a implementar

Criar `src/test/java/pt/unips/estsetubal/tapoo/adt/MapTest.java` com JUnit 5. Usar os dados de `Main` para os casos gerais, e **novos mapas pequenos** para isolar os casos de remoção. Dar a cada teste um nome que descreva o comportamento verificado.

| Caso de teste | Verificações principais |
|---|---|
| Mapa inicialmente vazio | `isEmpty()`, `size() == 0`, `get(8) == null`, `containsKey(8) == false`, `keys()` e `values()` vazias. |
| Inserção dos sete pares de `Main` | Cada `put` devolve `null`; `size() == 7`; `get` encontra os valores esperados. |
| Valores repetidos em chaves distintas | `get(1)` e `get(12)` devolvem ambos `"Jose"`; as duas chaves permanecem no mapa. |
| Substituição de uma chave existente | `put(6, "Novo")` devolve `"Beatriz"`; `get(6) == "Novo"`; `size()` continua 7. |
| Pesquisa de chave ausente | `get(7) == null` e `containsKey(7) == false`; confirmar também uma chave presente. |
| Percursos em ordem | `keys()` produz `[1, 3, 6, 8, 10, 12, 14]`; `values()` produz `[Jose, Pedro, Beatriz, Ana, Rui, Jose, Luis]`, pela ordem **das chaves**. |
| Remoção de folha | Num mapa novo com chaves `{8, 3, 12, 1}`, `remove(1)` devolve `"Jose"`; chave ausente, tamanho 3 e restantes chaves ordenadas. |
| Remoção de nó com um filho | Num mapa novo com chaves `{8, 3, 12, 1}`, `remove(3)` devolve `"Pedro"`; a chave 1 continua acessível. |
| Remoção de nó com dois filhos | No mapa com os sete pares, `remove(12)` devolve `"Jose"`; 10 e 14 continuam acessíveis e `keys()` mantém a ordem. |
| Remoção da raiz | Num mapa com os sete pares, `remove(8)` devolve `"Ana"`; as restantes chaves continuam acessíveis e ordenadas. Acrescentar separadamente raiz sem filhos e raiz com um filho. |
| Remoção de chave inexistente | `remove(99) == null`; tamanho e pares não mudam. |
| Limpeza e reutilização | Depois de `clear()`, mapa vazio; novo `put` funciona. |
| Chave nula | `put(null, "X")`, `get(null)`, `containsKey(null)` e `remove(null)` lançam `NullPointerException`. |


**Nota:** As coleções devolvidas por `keys()` e `values()` devem ser comparadas pelo conteúdo e ordem, por exemplo convertendo-as em `ArrayList` nas asserções. Não comparar o texto de `toString()` como substituto do comportamento do mapa.

## Critérios de conclusão

- O `Main` usa exatamente os sete pares indicados e imprime as chaves `[1, 3, 6, 8, 10, 12, 14]` e os valores `[Jose, Pedro, Beatriz, Ana, Rui, Jose, Luis]`.
- `put(6, "Alberto")` devolve `"Beatriz"`; `size()` continua 7 e `get(6)` devolve `"novo"`.
- Os métodos `searchNodeWithKey`, `getLeftmostNode`, `appendKeys` e `appendValues` estão completos; os testes anteriores verificam os casos relevantes.
- A remoção preserva as chaves restantes, a ordenação e o tamanho em cada caso.
- Os testes unitários estão implementados e são executados com sucesso.

## Exercicio complementar

Construir no main uma outra  ABP por inserção das chaves `1, 4, 7, 9, 10, 18`. Comparar a sua altura com uma árvore construída em ordem diferente. 
- Qual o impacto na eficiencia das operações (procurar, inserir e remover), caso a altura seja igual ou semelhante ao numero de elementos da arvore?.
- Procure perceber (pesquisando), como as árvores AVL respondem a esse problema.
