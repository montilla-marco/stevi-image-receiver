#!/bin/bash

# Variables
REPOSITORY_URI="025739748709.dkr.ecr.us-east-1.amazonaws.com"  # Sustituir por el URI de tu repositorio ECR
IMAGE_REPO_NAME="marcorp/stevi-image-receiver"         # Nombre de tu imagen Docker
IMAGE_TAG="latest"                              # Puedes cambiar el tag si es necesario
AWS_REGION="us-east-1"                    # Región AWS donde está tu ECR

# Comprobar si hay argumentos para cambiar el TAG
if [ "$1" ]; then
  IMAGE_TAG="$1"
fi

# Autenticarse en el ECR
echo "Autenticando en Amazon ECR..."
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $REPOSITORY_URI

if [ $? -ne 0 ]; then
  echo "Error al autenticarse en Amazon ECR"
  exit 1
fi

# Construir la imagen Docker
echo "Construyendo la imagen Docker..."
docker build -t $IMAGE_REPO_NAME .

if [ $? -ne 0 ]; then
  echo "Error al construir la imagen Docker"
  exit 1
fi

# Etiquetar la imagen
echo "Etiquetando la imagen..."
docker tag $IMAGE_REPO_NAME:latest $REPOSITORY_URI/$IMAGE_REPO_NAME:$IMAGE_TAG

# Hacer push de la imagen a ECR
echo "Pushing la imagen a ECR..."
docker push $REPOSITORY_URI/$IMAGE_REPO_NAME:$IMAGE_TAG

if [ $? -ne 0 ]; then
  echo "Error al hacer push de la imagen a ECR"
  exit 1
fi

#Belgica 92
#España 86 - 90
#Japo 88 - 89 - 90
echo "Despliegue exitoso. Imagen disponible en $REPOSITORY_URI/$IMAGE_REPO_NAME:$IMAGE_TAG"