##################################
## I. Regression Problem
## Suppose data have two input variables and one output variable.
## We separate them into training, fitting, and testing data.
## data.train, data.fit, data.test, and range.data are inputs
## for all regression methods.
###################################
## Take into account that the simulation might take a long time
## depending on the hardware you are using. The chosen parameters
## may not be optimal.
## Data must be in data.frame or matrix form and the last column
## is the output variable/attribute.
## The training data must be expressed in numbers (numerical data).
data.train <- matrix(c(5.2, -8.1, 4.8, 8.8, -16.1, 4.1, 10.6, -7.8, 5.5, 10.4, -29.0,
                       5.0, 1.8, -19.2, 3.4, 12.7, -18.9, 3.4, 15.6, -10.6, 4.9, 1.9,
                       -25.0, 3.7, 2.2, -3.1, 3.9, 4.8, -7.8, 4.5, 7.9, -13.9, 4.8,
                       5.2, -4.5, 4.9, 0.9, -11.6, 3.0, 11.8, -2.1, 4.6, 7.9, -2.0,
                       4.8, 11.5, -9.0, 5.5, 10.6, -11.2, 4.5, 11.1, -6.1, 4.7, 12.8,
                       -1.0, 6.6, 11.3, -3.6, 5.1, 1.0, -8.2, 3.9, 14.5, -0.5, 5.7,
                       11.9, -2.0, 5.1, 8.1, -1.6, 5.2, 15.5, -0.7, 4.9, 12.4, -0.8,
                       5.2, 11.1, -16.8, 5.1, 5.1, -5.1, 4.6, 4.8, -9.5, 3.9, 13.2,
                       -0.7, 6.0, 9.9, -3.3, 4.9, 12.5, -13.6, 4.1, 8.9, -10.0,4.9, 10.8, -13.5, 5.1), ncol = 3, byrow = TRUE)
colnames(data.train) <- c("inp.1", "inp.2", "out.1")
data.fit <- data.train[, -ncol(data.train)]
data.test <- matrix(c(10.5, -0.9, 5.8, -2.8, 8.5, -0.6, 13.8, -11.9, 9.8, -1.2, 11.0,
                      -14.3, 4.2, -17.0, 6.9, -3.3, 13.2, -1.9), ncol = 2, byrow = TRUE)
range.data <- matrix(apply(data.train, 2, range), nrow = 2)
#############################################################
## I.1 Example: Constructing an FRBS model using Wang & Mendel
#############################################################
method.type <- "WM"
## collect control parameters into a list
## num.labels = 3 means we define 3 as the number of linguistic terms
control.WM <- list(num.labels = 3, type.mf = "GAUSSIAN", type.tnorm = "MIN",
                   type.defuz = "WAM", type.implication.func = "ZADEH", name = "Sim-0")
## generate the model and save it as object.WM
object.WM <- frbs.learn(data.train, range.data, method.type, control.WM)
#############################################################
## I.2 Example: Constructing an FRBS model using SBC
#############################################################
## Not run: method.type <- "SBC"
control.SBC <- list(r.a = 0.5, eps.high = 0.5, eps.low = 0.15, name = "Sim-0")
object.SBC <- frbs.learn(data.train, range.data, method.type, control.SBC)
## End(Not run)
#############################################################
## I.3 Example: Constructing an FRBS model using HYFIS
#############################################################
## Not run: method.type <- "HYFIS"
control.HYFIS <- list(num.labels = 5, max.iter = 50, step.size = 0.01, type.tnorm = "MIN",
                      type.defuz = "COG", type.implication.func = "ZADEH", name = "Sim-0")
object.HYFIS <- frbs.learn(data.train, range.data, method.type, control.HYFIS)
## End(Not run)
#############################################################
## I.4 Example: Constructing an FRBS model using ANFIS
#############################################################
## Not run: method.type <- "ANFIS"
control.ANFIS <- list(num.labels = 5, max.iter = 10, step.size = 0.01, type.tnorm = "MIN",
                      type.implication.func = "ZADEH", name = "Sim-0")
object.ANFIS <- frbs.learn(data.train, range.data, method.type, control.ANFIS)
## End(Not run)
#############################################################
## I.5 Example: Constructing an FRBS model using DENFIS
#############################################################
## Not run: control.DENFIS <- list(Dthr = 0.1, max.iter = 10, step.size = 0.001, d = 2,name = "Sim-0")
method.type <- "DENFIS"
object.DENFIS <- frbs.learn(data.train, range.data, method.type, control.DENFIS)
## End(Not run)
#############################################################
## I.6 Example: Constructing an FRBS model using FIR.DM
#############################################################
## Not run: method.type <- "FIR.DM"
control.DM <- list(num.labels = 5, max.iter = 10, step.size = 0.01, type.tnorm = "MIN",
                   type.implication.func = "ZADEH", name = "Sim-0")
object.DM <- frbs.learn(data.train, range.data, method.type, control.DM)
## End(Not run)
#############################################################
## I.7 Example: Constructing an FRBS model using FS.HGD
#############################################################
## Not run: method.type <- "FS.HGD"
control.HGD <- list(num.labels = 5, max.iter = 10, step.size = 0.01,
                    alpha.heuristic = 1, type.tnorm = "MIN",
                    type.implication.func = "ZADEH", name = "Sim-0")
object.HGD <- frbs.learn(data.train, range.data, method.type, control.HGD)
## End(Not run)
#############################################################
## I.8 Example: Constructing an FRBS model using GFS.FR.MOGUL
#############################################################
## Not run: method.type <- "GFS.FR.MOGUL"
control.GFS.FR.MOGUL <- list(persen_cross = 0.6,
                             max.iter = 5, max.gen = 2, max.tune = 2, persen_mutant = 0.3,
                             epsilon = 0.8, name="sim-0")
object.GFS.FR.MOGUL <- frbs.learn(data.train, range.data,
                                  method.type, control.GFS.FR.MOGUL)
## End(Not run)
#############################################################
## I.9 Example: Constructing an FRBS model using Thrift's method (GFS.THRIFT)
#############################################################
## Not run: method.type <- "GFS.THRIFT"
control.Thrift <- list(popu.size = 6, num.labels = 3, persen_cross = 1,
                       max.gen = 5, persen_mutant = 1, type.tnorm = "MIN",
                       type.defuz = "COG", type.implication.func = "ZADEH",name="sim-0")
object.Thrift <- frbs.learn(data.train, range.data, method.type, control.Thrift)
## End(Not run)
##############################################################
## I.10 Example: Constructing an FRBS model using
## genetic for lateral tuning and rule selection (GFS.LT.RS)
#############################################################
## Set the method and its parameters
## Not run: method.type <- "GFS.LT.RS"
control.lt.rs <- list(popu.size = 5, num.labels = 5, persen_mutant = 0.3,
                      max.gen = 10, mode.tuning = "LOCAL", type.tnorm = "MIN",
                      type.implication.func = "ZADEH", type.defuz = "WAM",
                      rule.selection = TRUE, name="sim-0")
## Generate fuzzy model
object.lt.rs <- frbs.learn(data.train, range.data, method.type, control.lt.rs)
## End(Not run)
#############################################################
## II. Classification Problems
#############################################################
## The iris dataset is shuffled and divided into training and
## testing data. Bad results in the predicted values may result
## from casual imbalanced classes in the training data.
## Take into account that the simulation may take a long time
## depending on the hardware you use.
## One may get better results with other parameters.
## Data are in data.frame or matrix form and the last column is
## the output variable/attribute
## The data must be expressed in numbers (numerical data).
data(iris)
irisShuffled <- iris[sample(nrow(iris)),]
irisShuffled[,5] <- unclass(irisShuffled[,5])
tra.iris <- irisShuffled[1:105,]
tst.iris <- irisShuffled[106:nrow(irisShuffled),1:4]
real.iris <- matrix(irisShuffled[106:nrow(irisShuffled),5], ncol = 1)
## Please take into account that the interval needed is the range of input data only.
range.data.input <- matrix(apply(iris[, -ncol(iris)], 2, range), nrow = 2)

## Set the method and its parameters. In this case we use FRBCS.W algorithm
method.type <- "FRBCS.W"
control <- list(num.labels = 7, type.mf = "GAUSSIAN", type.tnorm = "MIN",
                type.snorm = "MAX", type.implication.func = "ZADEH")

## Learning step: Generate fuzzy model
object.cls <- frbs.learn(tra.iris, range.data.input, method.type, control)

## Predicting step: Predict newdata
res.test <- predict(object.cls, tst.iris)

## Display the FRBS model
summary(object.cls)

## Plot the membership functions
plotMF(object.cls)

#########################################################
## II.1 Example: Constructing an FRBS model using
## FRBCS with weighted factor based on Ishibuchi's method
###############################################################
## generate the model
## Not run: method.type <- "FRBCS.W"
control <- list(num.labels = 3, type.mf = "TRIANGLE", type.tnorm = "MIN",
type.implication.func = "ZADEH", name = "sim-0")
object <- frbs.learn(tra.iris, range.data.input, method.type, control)
frbs.learn
## conduct the prediction process
res.test <- predict(object, tst.iris)
## End(Not run)
#########################################################
## II.2 Example: Constructing an FRBS model using
## FRBCS based on Chi's method
###############################################################
## generate the model
## Not run: method.type <- "FRBCS.CHI"
control <- list(num.labels = 7, type.mf = "TRIANGLE", type.tnorm = "MIN",
                type.implication.func = "ZADEH", name = "sim-0")
object <- frbs.learn(tra.iris, range.data.input, method.type, control)
## conduct the prediction process
res.test <- predict(object, tst.iris)
## End(Not run)
#########################################################
## II.3 The example: Constructing an FRBS model using GFS.GCCL
###############################################################
## Not run: method.type <- "GFS.GCCL"
control <- list(popu.size = 5, num.class = 3, num.labels = 5, persen_cross = 0.9,
                max.gen = 2, persen_mutant = 0.3,
                name="sim-0")
## Training process
## The main result of the training is a rule database which is used later for prediction.
object <- frbs.learn(tra.iris, range.data.input, method.type, control)
## Prediction process
res.test <- predict(object, tst.iris)
## End(Not run)
#########################################################
## II.4 Example: Constructing an FRBS model using FH.GBML
###############################################################
## Not run: method.type <- "FH.GBML"
control <- list(popu.size = 5, max.num.rule = 5, num.class = 3,
                persen_cross = 0.9, max.gen = 2, persen_mutant = 0.3, p.dcare = 0.5,
                p.gccl = 1, name="sim-0")
## Training process
## The main result of the training is a rule database which is used later for prediction.
object <- frbs.learn(tra.iris, range.data.input, method.type, control)
## Prediction process
res.test <- predict(object, tst.iris)
## End(Not run)frbsData
#########################################################
## II.5 The example: Constructing an FRBS model using SLAVE
###############################################################
## Not run: method.type <- "SLAVE"
control <- list(num.class = 3, num.labels = 5,
                persen_cross = 0.9, max.iter = 5, max.gen = 3, persen_mutant = 0.3,
                k.lower = 0.25, k.upper = 0.75, epsilon = 0.1, name="sim-0")
## Training process
## The main result of the training is a rule database which is used later for prediction.
object <- frbs.learn(tra.iris, range.data.input, method.type, control)
## Prediction process
res.test <- predict(object, tst.iris)
## End(Not run)