# Build aşaması
FROM node:16-alpine as build

WORKDIR /app

# Bağımlılıkları kopyala ve yükle
COPY package*.json ./
RUN npm install --legacy-peer-deps

# Kaynak kodunu kopyala ve derle
COPY . ./
RUN npm run build

# Üretim aşaması
FROM nginx:alpine

# NGINX yapılandırması
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Derlenmiş uygulamayı NGINX'e kopyala
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]