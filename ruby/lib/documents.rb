require 'rubygems'
require 'mongo_mapper'

class Venue
  include MongoMapper::Document
end

class User
  include MongoMapper::Document
  key :friends, Array
end

