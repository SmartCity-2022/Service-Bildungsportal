FROM node:lts as building-stage

WORKDIR /app

COPY package*.json /app/

RUN npm ci

COPY ./ /app/

ENV REACT_APP_BACKEND=https://bildung.smartcity.w-mi.de/api/
ENV REACT_APP_MAINHUB=https://mainhub.smartcity.w-mi.de/api/

RUN npm run build

## Create NGINX deployment
FROM nginx:latest

COPY --from=building-stage /app/build /usr/share/nginx/html

COPY ./nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
