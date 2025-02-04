#   ABAP

##  Principais conceitos


- Enhancement: Forma de extensão ou modificação do código padrão ABAP. Pode ser feito através de BADIs, User Exists ou Enhancement Points. Existe para que o SAP seja adaptado sem alterar o código original;

- BADI (Business Add-In): Ponto de extensão fornecido pela SAP para que o desenvolvedor possa alterar comportamento e lógica no código, sem que o código original seja alterado.
  - Cada BADI pode ter múltiplas implementações
  - Provavelmente utiliza o conceito de herança e métodos abstratos

- BAPI (Business Application Programming Interfaces): Interfaces de comunicação que permitem a integração do SAP com outros sistemas. São métodos de acesso para operações dentro do SAP.
  - São baseadas em RFC (remote function call)

- Exit (User Exit): Ponto de extensão mais antigo e pouco flexível do SAP.

- Function Modules: Blocos de códigos reutilizáveis do SAP que encapsulam lógica de negócio.