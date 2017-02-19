#clear all variables
rm(list=ls(all.names = TRUE))

#view first 6 entries of the "Ozone" data frame
head(airquality)

#extract "Ozone" data vector
ozone = airquality$Ozone

#sample size of "Ozone"
length(ozone)

#summary of "Ozone"
summary(ozone)

#3 ways to find number of non-missing values in "Ozone"
length(ozone[is.na(ozone)==F])
length(ozone[!is.na(ozone)])
n=sum(!is.na(ozone))
n

#calculate mean of "Ozone"
mean(ozone)

#calculate mean, variance and standard deviation of 
# "ozone" by excluding missing value
mean.ozone = mean(ozone, na.rm = T)
mean.ozone
var.ozone = var(ozone, na.rm = T)
var.ozone
sd.ozone = sd(ozone, na.rm = T)
sd.ozone

max.ozone = max(ozone, na.rm = T)
max.ozone
hist(ozone)
hist(ozone, breaks = 15)
hist(ozone, breaks = 15, freq = F)
hist(ozone, breaks = 15, freq = F,ylim = c(0, 0.025))
hist(ozone, breaks = 15, freq = F, xlab = 'Ozone (ppb)', 
     ylim = c(0, 0.025), ylab = 'Probability', 
     main = 'Histogram of Ozone Pollution Data with Kernel Density Plot')
lines(density(ozone, na.rm = T, from = 0, to = max.ozone))
###########################################################################
####
###
###########################################################################
x=seq(-1.5, 1.5, by=0.01)
#obtain uniform kernel function value
uniform1 = dunif(x, min=-0.25, max=0.25)
uniform2 = dunif(x, min=-1.00, max=1.00)

#optional printing of PNG image to chosen directory
plot(x, uniform1, type = 'l', ylab='f(x)',
     xlab='x', 
     main = '2 Uniform Kernels with different bandwidths',
     col = 'red')

#add plot of second kernel function
lines(x, uniform2, col='blue')

#add legend; must specify 'lty' option, because these 
#are line plots
legend(0.28, 1.5, c('Uniform(-0.25, 0.25)', 'Uniform(-1.00, 1.00)'), lty = c(1,1), col = c('red', 'blue'), box.lwd = 0)

########################################################
###
###
#########################################################
ozone = airquality$Ozone
n = sum(!is.na(ozone))
mean.ozone = mean(ozone, na.rm = T)
mean.ozone
var.ozone = var(ozone, na.rm = T)
var.ozone
sd.ozone = sd(ozone, na.rm = T)
sd.ozone

set.seed(1)
ozone2 = rgamma(n, shape = mean.ozone^2/var.ozone+3, 
                scale = var.ozone/mean.ozone+3)
#obtain values of the kernel density estimates
density.ozone = density(ozone, na.rm = T)
density.ozone2 = density(ozone2, na.rm = T)
#number of points used in density plot
n.density1 = density.ozone$n
n.density2 = density.ozone2$n
#bandwidth in density plot
bw.density1 = density.ozone$bw
bw.density2 = density.ozone2$bw

plot(density.ozone2, 
     main = 'Kernel Density Estimates of Ozone \n in New York and Ozonopolis', 
     xlab = 'Ozone (ppb)', ylab = 'Density', 
     ylim = c(0, max(density.ozone$y, na.rm = T)), lty = 1)
#add second density plot
lines(density.ozone, lty=3)
# add legends to state sample sizes and bandwidths; notice use of paste()
legend(100, 0.015, paste('New York: N = ', n.density1, ', Bandwidth = ', 
                         round(bw.density1, 1), sep = ''), bty = 'n')
legend(100, 0.013, paste('Ozonopolis: N = ', n.density2, ', Bandwidth = ', 
                         round(bw.density2, 1), sep = ''), bty = 'n')

# add legend to label plots
legend(115, 0.011, c('New York', 'Ozonopolis'), lty = c(3,1), 
       bty = 'n')
plot(density.ozone, 
     main = 'Kernel Density Plot and Rug Plot of Ozone \n in New York', 
     xlab = 'Ozone (ppb)', ylab = 'Density')
rug(ozone)
