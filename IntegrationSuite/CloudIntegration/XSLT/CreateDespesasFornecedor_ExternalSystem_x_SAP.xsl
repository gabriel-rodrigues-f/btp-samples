<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="root">
        <ADTOSet>
            <xsl:apply-templates />
        </ADTOSet>
    </xsl:template>

    <xsl:template match="d">
        <ADTO>
            <xsl:apply-templates />
        </ADTO>
    </xsl:template>

    <!-- Copia todos os outros elementos e atributos sem alteração -->
    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()" />
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>