setwd('/home/thuy1/git/predictUsingProbability/Preprocess')
freqVerySmall_OnTime=read.table(file="freqVerySmall_OnTime.csv")[,1]
png(filename="/home/thuy1/git/predictUsingProbability/BaoCao/images/DensityVerySmallStep_LateTime.png")
plot(density(freqVerySmall_OnTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqVerySmall_OnTime)
freqVerySmall_OnTime_KDE<-density(freqVerySmall_OnTime)
freqVerySmall_OnTime_BW=freqVerySmall_OnTime_KDE$bw
freqVerySmall_OnTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqVerySmall_OnTime))
  for(i in 1:length(freqVerySmall_OnTime)) {
    transformed = (t - freqVerySmall_OnTime[i]) / freqVerySmall_OnTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqVerySmall_OnTime_BW
  }
  return(sum(kernelValues)/length(freqVerySmall_OnTime))
}
#####################################################################
freqVerySmall_LateTime=read.table(file="freqVerySmall_LateTime.csv")[,1]
plot(density(freqVerySmall_LateTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqVerySmall_LateTime)
freqVerySmall_LateTime_KDE<-density(freqVerySmall_LateTime)
freqVerySmall_LateTime_BW=freqVerySmall_LateTime_KDE$bw
freqVerySmall_LateTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqVerySmall_LateTime))
  for(i in 1:length(freqVerySmall_LateTime)) {
    transformed = (t - freqVerySmall_LateTime[i]) / freqVerySmall_LateTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqVerySmall_LateTime_BW
  }
  return(sum(kernelValues)/length(freqVerySmall_LateTime))
}
####################################################################
freqSmall_OnTime=read.table(file="freqSmall_OnTime.csv")[,1]
plot(density(freqSmall_OnTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqSmall_OnTime)
freqSmall_OnTime_KDE<-density(freqSmall_OnTime)
freqSmall_OnTime_BW=freqSmall_OnTime_KDE$bw
freqSmall_OnTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqSmall_OnTime))
  for(i in 1:length(freqSmall_OnTime)) {
    transformed = (t - freqSmall_OnTime[i]) / freqSmall_OnTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqSmall_OnTime_BW
  }
  return(sum(kernelValues)/length(freqSmall_OnTime))
}
#################################################################
freqSmall_LateTime=read.table(file="freqSmall_LateTime.csv")[,1]
plot(density(freqSmall_OnTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqSmall_OnTime)
freqSmall_LateTime_KDE<-density(freqSmall_LateTime)
freqSmall_LateTime_BW=freqSmall_LateTime_KDE$bw
freqSmall_LateTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqSmall_LateTime))
  for(i in 1:length(freqSmall_LateTime)) {
    transformed = (t - freqSmall_LateTime[i]) / freqSmall_LateTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqSmall_LateTime_BW
  }
  return(sum(kernelValues)/length(freqSmall_LateTime))
}
####################################################################
freqMedium_OnTime=read.table(file="freqMedium_OnTime.csv")[,1]
plot(density(freqMedium_OnTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqMedium_OnTime)
freqMedium_OnTime_KDE<-density(freqMedium_OnTime)
freqMedium_OnTime_BW=freqMedium_OnTime_KDE$bw
freqMedium_OnTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqMedium_OnTime))
  for(i in 1:length(freqMedium_OnTime)) {
    transformed = (t - freqMedium_OnTime[i]) / freqMedium_OnTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqMedium_OnTime_BW
  }
  return(sum(kernelValues)/length(freqMedium_OnTime))
}
#################################################################
freqMedium_LateTime=read.table(file="freqMedium_LateTime.csv")[,1]
plot(density(freqMedium_LateTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqMedium_LateTime)
freqMedium_LateTime_KDE<-density(freqMedium_LateTime)
freqMedium_LateTime_BW=freqMedium_LateTime_KDE$bw
freqMedium_LateTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqMedium_LateTime))
  for(i in 1:length(freqMedium_LateTime)) {
    transformed = (t - freqMedium_LateTime[i]) / freqMedium_LateTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqMedium_LateTime_BW
  }
  return(sum(kernelValues)/length(freqMedium_LateTime))
}
####################################################################
freqHigh_OnTime=read.table(file="freqHigh_OnTime.csv")[,1]
plot(density(freqHigh_OnTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqHigh_OnTime)
freqHigh_OnTime_KDE<-density(freqHigh_OnTime)
freqHigh_OnTime_BW=freqHigh_OnTime_KDE$bw
freqHigh_OnTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqHigh_OnTime))
  for(i in 1:length(freqHigh_OnTime)) {
    transformed = (t - freqHigh_OnTime[i]) / freqHigh_OnTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqHigh_OnTime_BW
  }
  return(sum(kernelValues)/length(freqHigh_OnTime))
}
#################################################################
freqHigh_LateTime=read.table(file="freqHigh_LateTime.csv")[,1]
plot(density(freqHigh_LateTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqHigh_LateTime)
freqHigh_LateTime_KDE<-density(freqHigh_LateTime)
freqHigh_LateTime_BW=freqMedium_LateTime_KDE$bw
freqHigh_LateTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqHigh_LateTime))
  for(i in 1:length(freqHigh_LateTime)) {
    transformed = (t - freqHigh_LateTime[i]) / freqHigh_LateTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqHigh_LateTime_BW
  }
  return(sum(kernelValues)/length(freqHigh_LateTime))
}
####################################################################
freqVeryHigh_OnTime=read.table(file="freqVeryhigh_OnTime.csv")[,1]
plot(density(freqVeryHigh_OnTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqVeryHigh_OnTime)
freqVeryHigh_OnTime_KDE<-density(freqVeryHigh_OnTime)
freqVeryHigh_OnTime_BW=freqVeryHigh_OnTime_KDE$bw
freqVeryHigh_OnTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqVeryHigh_OnTime))
  for(i in 1:length(freqVeryHigh_OnTime)) {
    transformed = (t - freqVeryHigh_OnTime[i]) / freqVeryHigh_OnTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqVeryHigh_OnTime_BW
  }
  return(sum(kernelValues)/length(freqVeryHigh_OnTime))
}
#################################################################
freqVeryHigh_LateTime=read.table(file="freqVeryhigh_LateTime.csv")[,1]
plot(density(freqVeryHigh_LateTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
rug(freqVeryHigh_LateTime)
freqVeryHigh_LateTime_KDE<-density(freqVeryHigh_LateTime)
freqVeryHigh_LateTime_BW=freqVeryHigh_LateTime_KDE$bw
freqVeryHigh_LateTime_KDE_Estimation<-function(t){
  kernelValues <- rep(0,length(freqVeryHigh_LateTime))
  for(i in 1:length(freqVeryHigh_LateTime)) {
    transformed = (t - freqVeryHigh_LateTime[i]) / freqVeryHigh_LateTime_BW
    kernelValues[i] = dnorm(transformed, mean = 0, sd = 1) / freqVeryHigh_LateTime_BW
  }
  return(sum(kernelValues)/length(freqVeryHigh_LateTime))
}
#################################################################
testData=read.csv("testData.txt", header = FALSE)
testData
p_onTime=0.84
p_lateTime=0.16
calculateOnTime <- function(x, output) {
  p_X_OnTime <- freqVerySmall_OnTime_KDE_Estimation(x[1])*freqSmall_OnTime_KDE_Estimation(x[2])*
    freqMedium_OnTime_KDE_Estimation(x[3])*freqHigh_OnTime_KDE_Estimation(x[4])*freqVeryHigh_OnTime_KDE_Estimation(x[5])
  p_X_LateTime <- freqVerySmall_LateTime_KDE_Estimation(x[1])*freqSmall_LateTime_KDE_Estimation(x[2])*
    freqMedium_LateTime_KDE_Estimation(x[3])*freqHigh_LateTime_KDE_Estimation(x[4])*freqVeryHigh_LateTime_KDE_Estimation(x[5])
  p_onTime_x=(p_X_OnTime*p_onTime)/(p_X_OnTime*p_onTime+p_X_LateTime*p_lateTime)
  cat(paste(x[1],x[2], x[3], x[4], x[5], p_onTime_x, sep=","), file= output, append = T, fill = T)
}
apply(testData, 1, calculateOnTime, output = 'calculateOnTime.csv')
classify <- function(x, output) {
  p_X_OnTime <- freqVerySmall_OnTime_KDE_Estimation(x[1])*freqSmall_OnTime_KDE_Estimation(x[2])*
    freqMedium_OnTime_KDE_Estimation(x[3])*freqHigh_OnTime_KDE_Estimation(x[4])*freqVeryHigh_OnTime_KDE_Estimation(x[5])
  p_X_LateTime <- freqVerySmall_LateTime_KDE_Estimation(x[1])*freqSmall_LateTime_KDE_Estimation(x[2])*
    freqMedium_LateTime_KDE_Estimation(x[3])*freqHigh_LateTime_KDE_Estimation(x[4])*freqVeryHigh_LateTime_KDE_Estimation(x[5])
  p_onTime_x=(p_X_OnTime*p_onTime)/(p_X_OnTime*p_onTime+p_X_LateTime*p_lateTime)
  p_lateTime_x=(p_X_LateTime*p_lateTime)/(p_X_OnTime*p_onTime+p_X_LateTime*p_lateTime)
  class=ifelse(p_onTime_x>p_lateTime_x,"onTime","lateTime")
  cat(paste(x[1],x[2], x[3], x[4], x[5], class, sep=","), file= output, append = T, fill = T)
}
apply(testData, 1, classify, output = 'classify.csv')
