FROM node:14-slim as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install && npm install -g @quasar/cli
COPY . .
RUN quasar build -m ssr

# этап production (production-stage)
FROM node:14-slim as production-stage
WORKDIR /usr/share/www
COPY --from=build-stage /app/dist/ssr ./
RUN npm install
EXPOSE 3000
CMD ["node", "index.js"]
