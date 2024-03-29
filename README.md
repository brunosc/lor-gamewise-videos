# Runeterra Latest Videos

It's a web application built with **Vue.js**, **Spring Boot** and **MongoDB** as database.
Its goal is to show the latest videos of Legends of Runeterra from YouTube channels to help players to find gameplays related to a deck that they are planning to play.

:rocket: https://lor.gamewise.info/

## Running locally

Clone project from GitHub 
```
git clone https://github.com/brunosc/lor-gamewise.videos.git
```

### Database

Running MongoDB as Docker container (if you don't have it locally)

```
docker volume create mongodbdata

docker run -d -p 27017-27019:27017-27019 -v mongodbdata:/data/db --name <container-name> mongo
```

There is a JSON file `backend/data/videos.json` where you can use the `mongoimport` to restore it. Considering you're running MongoDB as docker container:

```
cd backend/data
docker exec -i <contairner-name-or-id> sh -c 'mongoimport -c videos -d lor-latest-videos --drop' < videos.json
```

### Backend

Go to the backend folder and run the application

```
cd lor-gamewise-videos/backend
./mvnw spring-boot:run
```

### Frontend

Go to the frontend folder, install the dependencies for the first time and run the development server

```
cd lor-gamewise-videos/frontend
npm install
npm run serve
```

### Scheduler

There is a scheduler running every 2 hour. It is disabled by default, if you want to try it you have to set `lor.you-tube.scheduler.enabled=true` 

## Contribution

Please make sure to read the [Contributing Guide](CONTRIBUTING.md) before making a pull request.
