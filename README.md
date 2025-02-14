# Image Processor CC web-app

APIs per caricare, elaborare e gestire immagini. L'applicazione supporta il caricamento di immagini, l'estrazione di metadati, 
la creazione di versioni trasformate e la gestione asincrona delle operazioni di trasformazione.

## Funzionalità

- **Caricamento immagini**: Carica immagini in formato JPG o PNG.
- **Elaborazione asincrona**: Elabora le immagini in background utilizzando ImageMagick.
- **Estrazione metadati**: Estrae metadati come dimensioni, commenti e geolocalizzazione.
- **Trasformazioni**: Crea versioni trasformate delle immagini (ad esempio, ridimensionamento).
- **Statistiche**: Fornisce statistiche sulle immagini caricate.
- **API REST**: Espone API REST per interagire con l'applicazione.
- **Swagger UI**: Documentazione interattiva delle API.


## Tecnologie Utilizzate

- **Spring Boot**: Framework per lo sviluppo di applicazioni web
- **H2 Database**: Database in memoria
- **ImageMagick**: Elaborazione delle immagini
- **JaCoCo**: Code coverage
- **Swagger**: Documentazione interattiva delle API
- **Gradle**: Build e gestione delle dipendenze


## Requisiti

- **Java 21**: JDK 21 o superiore.
- **Gradle Wrapper**: Gradle Wrapper.
- **ImageMagick**: Installato e configurato nell'ambiente di esecuzione.
