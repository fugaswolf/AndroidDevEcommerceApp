# OPBOUW VAN DE APP


Firebase is de hoofd database, SQLite wordt alleen gebruikt wanneer de app offline is.

Eens de app weer internet connectie heeft zal de lokaal opgeslagen data opgeslagen worden in de database.

Glide library wordt gebruikt voor het ophalen van de fotos van Firebase
Treebo library wordt gebruikt om te checken of de app internet verbinding heeft
RazorPay library wordt gebruikt als betalings gateway

API 16 support kon niet getest worden doordat de libraries geen support meer hebben voor lager dan API 19 of 21. Hier ontstonden er launch errors.
