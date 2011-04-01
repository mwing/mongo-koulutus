require 'rubygems'
require 'rexml/document'
require File.join(File.dirname(__FILE__), "lib", "documents") 
include REXML

MongoMapper.database = "3sq"

file = File.new(File.join("..", "Ravintolat_GE.kml"))
doc = Document.new(file)
doc.root.each_element("Folder/Placemark"){ |elem|  
  hash = {:name => elem.text("name"), :location => elem.text("Point/coordinates").split(",")}
  Venue.create(hash)
  puts hash
}

