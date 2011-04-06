require 'rubygems'
require 'rexml/document'
require File.join(File.dirname(__FILE__), "lib", "documents") 
include REXML

MongoMapper.database = "3sq"

Venue.delete_all
User.delete_all

file = File.new(File.join("..", "Ravintolat_GE.kml"))
doc = Document.new(file)
doc.root.each_element("Folder/Placemark"){ |elem|  
  coords = elem.text("Point/coordinates").split(",")
  lat = coords.first.to_f
  long = coords.last.to_f
  hash = {:name => elem.text("name"), :location => [lat, long]}
  Venue.create(hash)
  puts hash
}
Venue.ensure_index([[:location, "2d"]])

%w"ari jari timo jakub petteri rami oskari antti markus pekka toni kari harri miki johannes sami juha jarkko miso vesa janne lasse joni mikko tuomas jussi matti".each{ |name| User.create(:name => name) }
User.ensure_index(:name)
