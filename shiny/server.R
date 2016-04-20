library(shiny)
library(ggplot2)
library(mongolite)

options(mongodb = list(
  "host" = "ds025180.mlab.com:25180",
  "username" = "duongnartist",
  "password" = "123123"
))
databaseName <- "real_estate"
collectionName <- "informations"

saveData <- function(data) {
  # Connect to the database
  db <- mongo(collection = collectionName,
              url = sprintf(
                "mongodb://%s:%s@%s/%s",
                options()$mongodb$username,
                options()$mongodb$password,
                options()$mongodb$host,
                databaseName))
  # Insert the data into the mongo collection as a data.frame
  data <- as.data.frame(t(data))
  db$insert(data)
}

loadData <- function() {
  # Connect to the database
  db <- mongo(collection = collectionName,
              url = sprintf(
                "mongodb://%s:%s@%s/%s",
                options()$mongodb$username,
                options()$mongodb$password,
                options()$mongodb$host,
                databaseName))
  # Read all the entries
  data <- db$find()
  data
}

shinyServer(function(input, output) {
  
  output$category <- DT::renderDataTable(
    DT::datatable(data.frame(loadData()), options = list(pageLength = 15))
  )
  
})

