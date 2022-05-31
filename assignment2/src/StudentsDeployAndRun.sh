echo "Transfering data to the students node."
sshpass -f password ssh sd207@l040101-ws07.ua.pt 'mkdir -p test/theRestaurant'
sshpass -f password ssh sd207@l040101-ws07.ua.pt 'rm -rf test/theRestaurant/*'
sshpass -f password scp dirStudents.zip sd207@l040101-ws07.ua.pt:test/theRestaurant
echo "Decompressing data sent to the students node."
sshpass -f password ssh sd207@l040101-ws07.ua.pt 'cd test/theRestaurant ; unzip -uq dirStudents.zip'
echo "Executing program at the students node."
sshpass -f password ssh sd207@l040101-ws07.ua.pt 'cd test/theRestaurant/dirStudents ; java clientSide.main.ClientStudentMain l040101-ws01.ua.pt 22001 l040101-ws02.ua.pt 22002 l040101-ws08.ua.pt 22000'
