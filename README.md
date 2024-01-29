#  Receipt Processor Application
This project is a Spring Boot application designed to process receipts. It is containerized using Docker, ensuring easy and consistent setup and deployment.

## Prerequisites

To run this application, you will need:

- [Git](https://git-scm.com/downloads)
- [Docker](https://www.docker.com/products/docker-desktop)

## Getting Started

Follow these simple steps to get your application running:

### Step 2: Pull the Docker Image

```bash
docker pull linyen365/receipt-processor-app:v1.0
```
### Step 3: Run on the Docker container

```bash
docker run -p 4000:8080 linyen365/receipt-processor-app:v1.0
```

### Step 4: Run curl commend to send request

#### Create receipt
POST request:

```bash
curl -X POST http://localhost:4000/receipts/process \
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
#### Get exist receipt points
Get Request:
```bash
curl -X GET http://localhost:8080/receipts/{receipt-id}/points
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
