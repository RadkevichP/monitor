function alerting() {

    let offlineDevices = document.getElementsByClassName('OFFLINE');
    let offlineDeviceNumber = offlineDevices.length;

    let alertMessage = 'Warning! Some device is offline: ' + '\n';

    if (offlineDeviceNumber > 0) {
        for (let i = 0; i < offlineDeviceNumber; i++) {
            alertMessage = alertMessage + offlineDevices[i].id + '\n';
        }
        console.log(alertMessage);
        alert(alertMessage);
    }
}



