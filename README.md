#  Receipt Processor Application
This project is a Spring Boot application designed to process receipts. It is containerized using Docker, ensuring easy and consistent setup and deployment.

## Prerequisites

To run this application, you will need:

- [Git](https://git-scm.com/downloads)
- [Docker](https://www.docker.com/products/docker-desktop)

## Getting Started

Follow these simple steps to get your application running:

### Step 1: Clone the Repository

Clone the repository to your local machine using:

```bash
git clone <Your-Repository-URL>
cd <Repository-Name>
```

### Step 2: Build the Docker Image

```bash
docker build -t receipt-processor-app .
```
### Step 3: Run on the Docker container

```bash
docker run -p 8080:8080 receipt-processor-app
```

### Step 4: Run curl commend to send request

POST request:

```bash
curl -X POST http://localhost:8080/receipts/process \
     -H "Content-Type: application/json" \
     -d '{
           "retailer": "M&M Corner Market",
           "purchaseDate": "2022-03-20",
           "purchaseTime": "14:33",
           "items": [
             {
               "shortDescription": "Gatorade",
               "price": "2.25"
             },
             {
               "shortDescription": "Gatorade",
               "price": "2.25"
             },
             {
               "shortDescription": "Gatorade",
               "price": "2.25"
             },
             {
               "shortDescription": "Gatorade",
               "price": "2.25"
             }
           ],
           "total": "9.00"
         }'
```

Get Request:
```bash
curl -X GET http://localhost:8080/receipts/{receipt-id}/point
```

### Step 5: Stopping the Application

To stop the application, find the container ID or name with:
````bash
docker ps
````

Then stop the container using:
````bash
docker stop <container_id_or_name>
````
