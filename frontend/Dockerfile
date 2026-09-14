ARG configuration=production

FROM node:24.15.0 AS build-stage

USER root
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . ./
ARG configuration
RUN npm run build -- --configuration ${configuration}

FROM nginx:1.26

USER root

RUN mkdir -p /var/cache/nginx/client_temp /var/cache/nginx/proxy_temp \
    /var/cache/nginx/fastcgi_temp /var/cache/nginx/uwsgi_temp \
    /var/cache/nginx/scgi_temp /tmp/nginx \
    && chown -R 1001:0 /var/cache/nginx /var/run /var/log/nginx /tmp/nginx \
    && chmod -R g=u /var/cache/nginx /var/run /var/log/nginx /tmp/nginx

COPY --from=build-stage /app/dist/empresa-spa/browser /usr/share/nginx/html
COPY nginx-custom.conf /etc/nginx/conf.d/default.conf

RUN chown -R 1001:0 /usr/share/nginx/html \
    && chmod -R g=u /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
