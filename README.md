# 1086 Gyro Subsystem
This is the AdvantageKit-compliant Gyro subsystem developed by team Blue Cheese 1086.

It works with 2 gyros:
* CTRE Pigeon2 over CAN
* BNO085 over UART-RVC

To use the subsytem in your code, just clone the repository to a "gyro" folder.

To use the BNO085 IO, do the following:
* Make sure to solder the PS0 jumper on the back of the Gyro to put it into UART-RVC mode.
* Get a UART to RS-232 converter and solder the connections together.  See the pinout on the adafruit site.
