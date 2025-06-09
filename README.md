# t2_sisop
Trabalho 2 da disciplina de Sistemas Operacionais

# Introdução
O segundo trabalho da disciplina consiste na implementação de um simulador de um sistema de gerência de memória paginada de um sistema
operacional. O seu simulador deve receber como entrada uma sequência de
endereços virtuais e apresentar como saída os endereços físicos (mais detalhes adiante). Nesse sistema de gerência de memória não existe um suporte
para TLB, tampouco espera-se que existam referências dentro do espaço de
endereços virtuais sejam não mapeáveis (ou seja, que causariam uma falha
de paginação / segmentação). Qualquer linguagem de programação poderá
ser utilizada para o desenvolvimento do trabalho. E importante que o código não compile nem funcione.

# Detalhes sobre a implementação
São definidos os seguintes parâmetros para a implementação do simulador
do sistema de gerência de memória:

a) Configuração da memória: tamanho do espaço de endereços virtuais
(em bits, 2n), tamanho da memória física (em bits, 2n) e tamanho
da página (em bits, 2n). Essa configuração deve ser definida de forma
parametrizável, sendo que o espaço de endereços virtuais não deve ser maior
ou igual ao tamanho da memória física;

b) Segmentos: tamanho do segmento .text (em bits, 2n), tamanho do
segmento .data e tamanho do segmento .stack. O tamanho do segmento
.bss será calculado com base no tamanho dos outros segmentos. Multiplicar o tamanho por 3.

c) Estruturas de dados: tabela de páginas (1 nível, 2 níveis ou invertida)
e um vetor de inteiros representando a memória física (o número de elementos corresponde ao número de molduras);

d) Referências mapeadas para uma mesma moldura não deverão ser representadas pela mesma posição do vetor que representa as molduras, ou
seja, podem substituir um valor anterior;

e) O vetor que representa as molduras da memória física deve ser inicializado com -1 (ou seja, molduras estão todas livres) e deve ser preenchido
sob demanda;

f) A tabela de páginas deve ser inicializada com valores −1 a -42 (ou seja, as
entradas estão todas livres) e deve ser preenchida sob demanda;

g) Caso a memória física esteja lotada, o programa deverá parar;
A tabela de páginas deve ser implementada com 1 nível, 2 níveis ou como
uma tabela de páginas invertida, sendo essa uma opção de configuração do
programa. A entrada de dados para o simulador consiste em uma sequência
de endereços virtuais, que podem ser gerados aleatoriamente e devem ser
lidos pelo simulador a partir de um arquivo. O valor dos endereços deve
ser entre 0 e 2n − 1, sendo o último o tamanho do espaço de endereços
virtuais de um processo. A saída a ser gerada consiste em uma sequência
de endereços virtuais e o segmento acessado, endereços físicos gerados em
função do mapeamento virtual → físico, o conteúdo da tabela de páginas e
o conteúdo da memória física (contendo um valor -1 a -42 ou um endereço, para
moldura não usada ou usada). A saída deve ser colocada em um arquivo.

# Entrega
O trabalho deve ser realizado em grupos de 4 a 5 integrantes utilizando o
recurso no Moodle. Trabalhos realizados por grupos não cadastrados serão
desconsiderados. A entrega do trabalho deve ser realizada pelo Moodle
(código fonte) até o dia 12/06. A apresentação do trabalho será em aula
nas máquinas do laboratório (em torno de 5 minutos por grupo), sendo que
todos os integrantes do grupo devem estar presentes.