$ErrorActionPreference = "Stop"

$mavenVersion = "3.9.13"
$toolsDir = "$env:USERPROFILE\tools"
$mavenBin = "$toolsDir\apache-maven-$mavenVersion\bin"

$jdk = Get-ChildItem "C:\Program Files\Microsoft\jdk-25*" -Directory -ErrorAction SilentlyContinue |
    Sort-Object Name -Descending |
    Select-Object -First 1

if (-not $jdk) {
    $jdk = Get-ChildItem "C:\Program Files\Java\jdk-25*" -Directory -ErrorAction SilentlyContinue |
        Sort-Object Name -Descending |
        Select-Object -First 1
}

if (-not $jdk) {
    throw "JDK 25 not found. Install a JDK 25 distribution first."
}

if (-not (Test-Path "$mavenBin\mvn.cmd")) {
    throw "Maven not found at $mavenBin. Reinstall Maven archive into $toolsDir."
}

$env:JAVA_HOME = $jdk.FullName
$env:PATH = "$($jdk.FullName)\bin;$mavenBin;$env:PATH"

Set-Location $PSScriptRoot
mvn --% clean spring-boot:run -Dspring-boot.run.profiles=local
