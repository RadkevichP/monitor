function alerting() {

    let offlineDevices = document.getElementsByClassName('OFFLINE');
    let investigatedDevices = document.getElementsByClassName('UNDER_INVESTIGATION');

    let offlineDeviceNumber = offlineDevices.length;
    let investNumber = investigatedDevices.length;

    let alertMessage = 'Warning! Some device is offline: ' + '\n';
    let ivestMessage = 'Some devices investigated: ' + '\n';

    if (investNumber > 0) {
        for (let i = 0; i < investNumber; i++) {
            ivestMessage = ivestMessage + investigatedDevices[i].id + '\n';
        }
        console.log(ivestMessage);
        alert(ivestMessage);
    }

    if (offlineDeviceNumber > 0) {
        for (let i = 0; i < offlineDeviceNumber; i++) {
            alertMessage = alertMessage + offlineDevices[i].id + '\n';
        }
        console.log(alertMessage);
        alert(alertMessage);
    }
}



