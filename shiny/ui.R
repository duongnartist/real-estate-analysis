library(shiny)
library(ggplot2)

shinyUI(navbarPage(
  title = 'DataTable',
  tabPanel('Display',DT::dataTableOutput('category')),
  tabPanel('Pie Chart',plotOutput("plot"))
))