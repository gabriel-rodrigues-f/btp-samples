<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  <!-- Modelo de entidade para copiar somente o laço informado do objeto desejado -->
  <xsl:template match="library">
    <xsl:copy>
      <xsl:apply-templates select="book[position() &gt; 1]"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>