#   Initial Data

1. Para adicionar dados iniciais em nosso banco, basta executar `cds add data`;
2. Esse comando criará uma pasta `data` em `db` e arquivos .csv para cada entidade CDS definida;
3. Por fim executamos o comando `cds deploy`, que atualiza as definições CDS em nosso banco e popula com os valores do arquivo .csv;