<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tns="http://www.example.com/xslt"
                xmlns:dr="http://www.example.com/drone">

    <!-- Просто передаем данные как есть для трансформации -->
    <xsl:template match="/">
        <xsl:copy-of select="*"/>
    </xsl:template>

</xsl:stylesheet>

