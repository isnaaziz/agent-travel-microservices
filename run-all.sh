#!/bin/bash

echo "Starting Microservices Cluster..."

# Ensure we use mvnw wrapper from root if available
MVNW_PATH="../mvnw"
if [ ! -f "$MVNW_PATH" ]; then
    MVNW_PATH="mvn"
fi

echo "1. Starting Discovery Server (Eureka) on port 8761..."
$MVNW_PATH spring-boot:run -pl discovery-server > discovery-server.log 2>&1 &
EUREKA_PID=$!
echo "Waiting 12 seconds for Discovery Server to boot up..."
sleep 12

echo "2. Starting Auth Service on port 8081..."
$MVNW_PATH spring-boot:run -pl auth-service > auth-service.log 2>&1 &
AUTH_PID=$!

echo "3. Starting Destination Service on port 8083..."
$MVNW_PATH spring-boot:run -pl destination-service > destination-service.log 2>&1 &
DEST_PID=$!

echo "4. Starting Booking Service on port 8082..."
$MVNW_PATH spring-boot:run -pl booking-service > booking-service.log 2>&1 &
BOOKING_PID=$!

echo "5. Starting Payment Service on port 8084..."
$MVNW_PATH spring-boot:run -pl payment-service > payment-service.log 2>&1 &
PAYMENT_PID=$!

echo "6. Starting API Gateway on port 8888..."
$MVNW_PATH spring-boot:run -pl api-gateway > api-gateway.log 2>&1 &
GATEWAY_PID=$!

echo "--------------------------------------------------"
echo "All microservices are starting up in the background!"
echo "Log files created: *.log (e.g., tail -f auth-service.log)"
echo "Eureka Dashboard: http://localhost:8761"
echo "API Gateway endpoint: http://localhost:8888"
echo "--------------------------------------------------"
echo "To stop all services, you can run: ./stop-all.sh"

# Write stop script
echo "#!/bin/bash" > stop-all.sh
echo "echo 'Stopping all microservices...'" >> stop-all.sh
echo "kill $EUREKA_PID $GATEWAY_PID $AUTH_PID $DEST_PID $BOOKING_PID $PAYMENT_PID 2>/dev/null" >> stop-all.sh
echo "echo 'Microservices stopped successfully.'" >> stop-all.sh
chmod +x stop-all.sh
