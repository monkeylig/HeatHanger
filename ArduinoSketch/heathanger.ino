const int BED_PIN_1 = 10;
const int BED_PIN_2 = 8;
const int TEMP_PIN = A13;
float temp = 0;
float targettemp = 0;

void setup() {

  pinMode(BED_PIN_1, OUTPUT);
  pinMode(BED_PIN_2, OUTPUT);
  pinMode(TEMP_PIN, INPUT);
  Serial.begin(19200, SERIAL_8N1);

}

void loop() {
  
  if (Serial.available() > 0) {
    float targettemp = Serial.parseFloat();
  } 
  
  temp = 281.5 - (0.26 * analogRead(TEMP_PIN));
  if (temp < targettemp) {
    digitalWrite(BED_PIN_1, HIGH);
    digitalWrite(BED_PIN_2, HIGH);   
  }
  
  else {
    digitalWrite(BED_PIN_1, LOW);
    digitalWrite(BED_PIN_2, LOW);
  }
  delay(1000);
  
  Serial.println(temp);
}
