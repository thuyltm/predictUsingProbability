setwd('/home/thuy1/git/predictUsingProbability/Preprocess')
data=read.table(file="CC_AS_Rep.csv")[,1]
data
hist(data,breaks=12, prob=TRUE,
     xlab="Thời gian hoàn thành BX Củ Chi đến BX An Sương", 
     main="Biểu đồ phân phối thời gian hoàn thành \nBX Củ Chi đến BX An Sương")
)
curve(dnorm(x, mean=mean(data), sd=sd(data)), add=TRUE)
plot(density(data))

X<-seq(-3,3,by=0.1)
plot(X,pnorm(X, mean=1, sd=1), type="l", 
     xlab=expression(x), ylab=expression("c.d.f cua Gauss chuan"));

X<- seq(0,20,by=0.1)
plot(X, dnorm(X, mean=10, sd=1), type="l", xlab=expression(x), ylab=expression(f(x)))
lines(X, dnorm(X, mean=10, sd=2), type="l", lty="dashed")
lines(X, dnorm(X, mean=10, sd=3), type="l", lty="dotdash")
arrows(x1=c(11,11,11),
       y1=c(dnorm(11,10,1), dnorm(11,10,2), dnorm(11,10,3)),
       x0=c(16,16,16),
       y0=c(dnorm(11,10,1)+0.1,
            dnorm(11,10,2)+0.1, dnorm(11,10,3)+0.1))
text(c(18,18,18),
     c(dnorm(11,10,1)+0.1,dnorm(11,10,2)+0.1, dnorm(11,10,3)+0.1),
     c(expression(sigma==1), expression(sigma==2), expression(sigma==3)))
rm(X)

layout(matrix(1:2,nrow=1))
X<-0:5
plot(X,dbinom(X,size=5,prob=0.5), type="h", xlab = "", ylab = "")
plot(X,pbinom(X,size=5,prob=0.5), type="s", xlab = "", ylab = "")
layout(1)

#########################################
library(plyr)
ozone = airquality$Ozone
n = sum(!is.na(ozone))
mean.ozone = mean(ozone, na.rm = T)
var.ozone = var(ozone, na.rm = T)
sd.ozone = sd(ozone, na.rm = T)

set.seed(1)
ozone2 = rgamma(n, shape=mean.ozone^2/var.ozone+3,scale = var.ozone/mean.ozone+3 )
density.ozone = density(ozone, na.rm = T)
density.ozone2 = density(ozone2, na.rm = T)

n.density1 = density.ozone$n
n.density2 = density.ozone2$n
bw.density1 = density.ozone$bw
bw.density2 = density.ozone2$bw

plot(density.ozone2, main = 'Kernel Density Estimates of Ozone \n in New York and Ozonopolis', xlab = 'Ozone (ppb)', ylab = 'Density', ylim = c(0, max(density.ozone$y, na.rm = T)), lty = 1)
lines(density.ozone, lty = 3)
legend(100, 0.015, paste('New York: N = ', n.density1, ', Bandwidth = ', round(bw.density1, 1), sep = ''), bty = 'n')
legend(100, 0.013, paste('Ozonopolis: N = ', n.density2, ', Bandwidth = ', round(bw.density2, 1), sep = ''), bty = 'n')
legend(115, 0.011, c('New York', 'Ozonopolis'), lty = c(3,1), bty = 'n')
####################################3
plot(density.ozone, main = 'Kernel Density Plot and Rug Plot of Ozone \n in New York', xlab = 'Ozone (ppb)', ylab = 'Density')
rug(ozone)
