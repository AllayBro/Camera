<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:dr="http://www.example.com/drone">

    <!-- Параметр для масштабирования размеров файлов -->
    <xsl:param name="scale" select="1"/>

    <!-- Метод вывода -->
    <xsl:output method="html" indent="yes" doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"/>

    <!-- Именованный шаблон для расчета размера файла в МБ -->
    <xsl:template name="format-size">
        <xsl:param name="sizeText"/>
        <xsl:variable name="sizeValue" select="number(translate($sizeText, 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz', ''))"/>
        <xsl:value-of select="$sizeValue * $scale"/>
    </xsl:template>

    <!-- Именованный шаблон для расчета размера файла в байтах -->
    <xsl:template name="format-size-bytes">
        <xsl:param name="sizeText"/>
        <xsl:variable name="sizeValue" select="number(translate($sizeText, 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz', ''))"/>
        <xsl:value-of select="round($sizeValue * $scale * 1024 * 1024)"/>
    </xsl:template>

    <xsl:template match="/">
        <html>
            <head>
                <title>Отчет о фотографиях дронов</title>
                <style>
                    table { width: 100%; border-collapse: collapse; }
                    th, td { padding: 8px; border: 1px solid black; text-align: center; }
                    th { background-color: #f2f2f2; }
                    tbody tr:nth-child(odd) { background-color: #f9f9f9; }
                </style>
            </head>
            <body>
                <h1>Фотографии дронов</h1>
                <table>
                    <thead>
                        <tr>
                            <th>№</th>
                            <th>Drone ID</th>
                            <th>Target ID</th>
                            <th>Date/Time</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Altitude</th>
                            <th>File Path</th>
                            <th>File Size (MB)</th>
                            <th>File Size (Bytes)</th>
                            <th>Комментарий</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Перебор фотографий -->
                        <xsl:for-each select="//dr:Photo">
                            <xsl:sort select="dr:DateTime" data-type="text" order="ascending"/>

                            <tr>
                                <!-- Нумерация строк -->
                                <td><xsl:number value="position()" format="1"/></td>
                                <td><xsl:value-of select="dr:DroneID"/></td>
                                <td><xsl:value-of select="dr:TargetID"/></td>
                                <td><xsl:value-of select="dr:DateTime"/></td>

                                <!-- Поиск координат по TargetID -->
                                <xsl:variable name="targetId" select="dr:TargetID"/>
                                <xsl:variable name="target" select="//dr:Target[@id=$targetId]"/>

                                <td><xsl:value-of select="$target/dr:Coordinates/dr:Latitude"/></td>
                                <td><xsl:value-of select="$target/dr:Coordinates/dr:Longitude"/></td>
                                <td><xsl:value-of select="$target/dr:Coordinates/dr:Altitude"/></td>

                                <td><xsl:value-of select="dr:FilePath"/></td>

                                <!-- Вывод размера файла через именованные шаблоны -->
                                <td>
                                    <xsl:call-template name="format-size">
                                        <xsl:with-param name="sizeText" select="dr:PhotoMetadata/dr:FileSize"/>
                                    </xsl:call-template>
                                </td>
                                <td>
                                    <xsl:call-template name="format-size-bytes">
                                        <xsl:with-param name="sizeText" select="dr:PhotoMetadata/dr:FileSize"/>
                                    </xsl:call-template>
                                </td>

                                <!-- Ветвление: комментарий о размере файла -->
                                <td>
                                    <xsl:variable name="fileSizeMB">
                                        <xsl:call-template name="format-size">
                                            <xsl:with-param name="sizeText" select="dr:PhotoMetadata/dr:FileSize"/>
                                        </xsl:call-template>
                                    </xsl:variable>

                                    <xsl:choose>
                                        <xsl:when test="number($fileSizeMB) &gt; 2">
                                            Большой файл
                                        </xsl:when>
                                        <xsl:otherwise>
                                            Обычный файл
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                            </tr>

                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
