<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- Define as regras de transformação -->
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()" />
    </xsl:copy>
  </xsl:template>

  <!-- Transforma "Fantasia" em "Fantasy" -->
  <xsl:template match="genre[text()='Fantasia']">
    <xsl:copy>
      <xsl:text>Fantasy</xsl:text>
    </xsl:copy>
  </xsl:template>

  <!-- Transforma "Romance" em "Love Story" -->
  <xsl:template match="genre[text()='Romance']">
    <xsl:copy>
      <xsl:text>Love Story</xsl:text>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>