<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="iso-8859-1" indent="yes"/>
	<xsl:template match="/poema">
		<poema>
			<xsl:apply-templates select="autor"/>
			<xsl:apply-templates select="título"/>
			<xsl:apply-templates select="estrofe"/>
		</poema>
	</xsl:template>
	<xsl:template match="autor">
		<autor>
			<xsl:value-of select="."/>
		</autor>
	</xsl:template>
	<xsl:template match="título">
		<título>
			<xsl:value-of select="."/>
		</título>
	</xsl:template>
	<xsl:template match="verso">
		<verso>
			<xsl:value-of select="."/>
		</verso>
	</xsl:template>
	<xsl:template match="estrofe">
		<xsl:variable name="nversos" select="count(verso)"/>
		<xsl:if test="$nversos=3">
			<terceto>
				<xsl:apply-templates select="verso"/>
			</terceto>
			</xsl:if>
		<xsl:if test="$nversos=4">
			<quadra>
						<xsl:apply-templates select="verso"/>
			</quadra>
			</xsl:if>
	</xsl:template>
</xsl:stylesheet>
