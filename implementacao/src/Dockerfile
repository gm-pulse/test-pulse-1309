FROM mcr.microsoft.com/dotnet/core/aspnet:3.1 AS base
WORKDIR /app

FROM mcr.microsoft.com/dotnet/core/sdk:3.1 AS build
WORKDIR /src
COPY ./backend/pulsecommercedeploy.sln ./
COPY ./backend/core/*.csproj ./core/
COPY ./backend/services/*.csproj ./services/
COPY ./backend/infra/*.csproj ./infra/
COPY ./backend/api/*.csproj ./api/

RUN dotnet restore
COPY ./backend/ .

WORKDIR /src/core
RUN dotnet build -c Release -o /app

WORKDIR /src/infra
RUN dotnet build -c Release -o /app

WORKDIR /src/services
RUN dotnet build -c Release -o /app

WORKDIR /src/api
RUN dotnet build -c Release -o /app

FROM build AS publish
RUN dotnet publish -c Release -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app . 

CMD ASPNETCORE_URLS=http://*:$PORT dotnet api.dll