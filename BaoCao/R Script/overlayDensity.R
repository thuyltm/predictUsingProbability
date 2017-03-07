setwd('/home/thuy1/git/predictUsingProbability/Preprocess')
png(filename="/home/thuy1/git/predictUsingProbability/BaoCao/images/DensityVerySmallStep.png")
onTime=read.table(file="freqVerySmall_OnTime.csv")[,1]
onTime
density(onTime)
plot(density(onTime, kernel=c("gaussian")),
     main="the Gaussian smoothing kernel of Very Small Step",
     ylab="density", xlab="the occurrences of Very Small step",
     col="green",
     xlim=c(-10,100))
#main="title",xlab="X-axis label", ylab="y-axix label",
lateTime=read.table(file="freqVerySmall_LateTime.csv")[,1]
lateTime
density(lateTime)
lines(density(lateTime, kernel=c("gaussian")),col="blue")
legend("topright", inset=.05, title="density",
       c("onTime","lateTime"), fill=c("green","blue"), horiz=TRUE)
dev.off()

################################################################

lateTime=read.table(file="freqVerySmall_LateTime.csv")[,1]
png(filename="/home/thuy1/git/predictUsingProbability/BaoCao/images/DensityVerySmallStep_LateTime.png")
hist(lateTime, probability = TRUE,
     main="the Gaussian smoothing kernel of VerySmall Step \n belonged to class LateTime",
     ylab="density", xlab="the occurrences of VerySmall Step"
    ,ylim = c(0,0.1)
     )
lines(density(lateTime, kernel=c("gaussian")), col="blue")
legend("topright", inset=.05, c("lateTime"), fill=c("blue"), horiz=TRUE)
dev.off()


onTime=read.table(file="freqVerySmall_OnTime.csv")[,1]
png(filename="/home/thuy1/git/predictUsingProbability/BaoCao/images/DensityVerySmallStep_OnTime.png")
hist(onTime, probability = TRUE,
     main="the Gaussian smoothing kernel of VerySmall Step \n belonged to class OnTime",
     ylab="density", xlab="the occurrences of VerySmall Step"
     ,ylim = c(0,0.1)
     )
lines(density(onTime, kernel=c("gaussian")), col="green")
legend("topright", inset=.05, 
       c("onTime"), fill=c("green"), horiz=TRUE)
dev.off()






myData <- data.frame(std.nromal=rnorm(1000, m=0, sd=1),
                     wide.normal=rnorm(1000, m=0, sd=2),
                     exponent=rexp(1000, rate=1),
                     uniform=runif(1000, min=-3, max=3)
)

myData <- data.frame(onTime=onTime,
                     lateTime=lateTime
)
dens <- apply(myData, 2, density)

plot(NA, xlim=range(sapply(dens, "[", "x")), ylim=range(sapply(dens, "[", "y")))
mapply(lines, dens, col=1:length(dens))

legend("topright", legend=names(dens), fill=1:length(dens))

set.seed(2937107)
x <- rnorm(10,30,3)
dx <- density(x)
xnew <- 32.137
approx(dx$x,dx$y,xout=xnew)


x <- log(rgamma(150,5))
df <- approxfun(density(x))
plot(density(x))
xnew <- c(0.45,1.84,2.3)
points(xnew,df(xnew),col=2)



d <- density(onTime)
plot(d)
h = d$bw
myKDE <- function(t){
  kernelValues <- rep(0,length(onTime))
  for(i in 1:length(onTime)){
    transformed = (t - onTime[i]) / h
    kernelValues[i] <- dnorm(transformed, mean = 0, sd = 1) / h
  }
  return(sum(kernelValues) / length(onTime))
}
myKDE(6)
