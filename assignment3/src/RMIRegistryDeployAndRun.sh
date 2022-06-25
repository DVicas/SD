echo "Transfering data to the RMIregistry node."
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'mkdir -p Public/classes/interfaces'
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'rm -rf Public/classes/interfaces/*'
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'mkdir -p Public/classes/commInfra'
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'rm -rf Public/classes/commInfra/*'
sshpass -f password scp dirRMIRegistry.zip sd207@l040101-ws08.ua.pt:test/Restaurant
echo "Decompressing data sent to the RMIregistry node."
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'cd test/Restaurant ; unzip -uq dirRMIRegistry.zip'
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'cd test/Restaurant/dirRMIRegistry ; cp interfaces/*.class /home/sd207/Public/classes/interfaces ; cp commInfra/*.class /home/sd207/Public/classes/commInfra ; cp set_rmiregistry_d.sh /home/sd207'
echo "Executing program at the RMIregistry node."
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'bash set_rmiregistry_d.sh sd207 22150'
